package group.networkinventorytask.company.inventory.dto.request;

public record UserCreateRequest(
        String username,
        String password
) {
}